package ru.cbr.rrror.service.order;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.cbr.rrror.service.order.entity.*;
import ru.cbr.rrror.service.order.repository.OrdinanceStatusRepository;
import ru.cbr.rrror.service.order.repository.OrdinanceRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@Component
public class DataGenerator implements CommandLineRunner {

    @Autowired
    private OrdinanceRepository repository;

    @Autowired
    private StatusLoader statusLoader;

    @Component
    static class StatusLoader {

        @Autowired
        private OrdinanceStatusRepository repository;

        public void load(List<OrdinanceStatus> stats) {
            repository.saveAll(stats);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug(">>> starting db data generation");
        List<CreditOrganization> banks = new CsvReader.CreditOrgReader(new ClassPathResource("banks.csv")).get();
        List<OrdinanceStatus> stats = new CsvReader.OrderStatusReader(new ClassPathResource("orderStatus.csv")).get();
        List<ReportPeriod> reportPeriods = new CsvReader.ReportPeriodReader(new ClassPathResource("reportPeriod.csv")).get();
        List<TuCbr> tuCbrList = new CsvReader.TuCbrReader(new ClassPathResource("tu.csv")).get();
        List<FundStatus> fundStatusList =  new CsvReader.FundStatusReader(new ClassPathResource("fundStatus.csv")).get();

        statusLoader.load(stats);

        List<Ordinance> results = new LinkedList<>();

        for (int i = 0; i < 12; i++) {
            ReportPeriod reportPeriod = reportPeriods.get(i);
            for (int k = 0; k < tuCbrList.size(); k++) {
                TuCbr tu = tuCbrList.get(k);
                int coShift = k * 10;
                for (int j = coShift; j < coShift + 10; j ++) {
                    CreditOrganization co = banks.get(j);

                    Ordinance order = new Ordinance();
                    order.setOrdinanceStatus(stats.get(0));
                    order.setFundStatus(new RandomObject<FundStatus>(fundStatusList).get());
                    order.setCreditOrganization(co);
                    order.setReportPeriod(reportPeriod);
                    order.setTuCbr(tu);
                    order.setCalcSpec(new CalcSpecGenerator().get());
                    order.setExecutionDetail(new ExecutionDetailGenerator(order).get());

                    results.add(order);
                }
            }
        }

        repository.saveAll(results);
    }

    @AllArgsConstructor
    static class ExecutionDetailGenerator implements Supplier<ExecutionDetail> {

        private final Random random = new Random();
        private final Ordinance order;

        @Override
        public ExecutionDetail get() {
            ExecutionDetail detail = new ExecutionDetail();
            detail.setRegDate(LocalDate.now());
            detail.setRegNum(random.nextInt(100) + "-" + random.nextInt(100));
            return detail;
        }
    }

    static class CalcSpecGenerator implements Supplier<Ordinance.CalcSpec> {

        private final Integer calcIdBound = 10000;
        private final Integer max = 9999999;
        private final Integer min = 999999;
        private final Random random = new Random();

        private double nextValue() {
            return (Math.random()*((max-min)+1))+min;
        }

        @Override
        public Ordinance.CalcSpec get() {
            double calcValue = nextValue();
            double factValue = nextValue();
            double value = calcValue - factValue;

            return new Ordinance.CalcSpec(
                    new Long(random.nextInt(calcIdBound)),
                    BigDecimal.valueOf(value),
                    BigDecimal.valueOf(calcValue),
                    BigDecimal.valueOf(factValue));
        }
    }

    @AllArgsConstructor
    static class RandomObject<T> implements Supplier<T> {

        private final Random random = new Random();
        private final List<T> values;

        @Override
        public T get() {
            return values.get(random.nextInt(values.size()));
        }
    }

    @AllArgsConstructor
    static abstract class CsvReader<T> implements Supplier<List<T>> {
        protected final List<T> results = new LinkedList<>();
        protected final Resource resource;

        protected abstract T csvLineTransform(String line);

        @Override
        public List<T> get() {
            try (Stream<String> stream = Files.lines(Paths.get(resource.getURI()))) {
                stream.forEach(line -> results.add(this.csvLineTransform(line)));

            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        static class FundStatusReader extends CsvReader<FundStatus> {

            public FundStatusReader(Resource resource) {
                super(resource);
            }

            @Override
            protected FundStatus csvLineTransform(String line) {
                String[] cols = line.split(";");
                return new FundStatus(Integer.valueOf(cols[0]), cols[1], cols[2]);
            }
        }

        static class TuCbrReader extends CsvReader<TuCbr> {

            public TuCbrReader(Resource resource) {
                super(resource);
            }

            @Override
            protected TuCbr csvLineTransform(String line) {
                String[] cols = line.split(";");
                return new TuCbr(Integer.valueOf(cols[0]), cols[1], cols[2], cols[3]);
            }
        }

        static class ReportPeriodReader extends CsvReader<ReportPeriod> {

            public ReportPeriodReader(Resource resource) {
                super(resource);
            }

            @Override
            protected ReportPeriod csvLineTransform(String line) {
                String[] cols = line.split(";");
                return new ReportPeriod(Integer.valueOf(cols[0]), cols[5], cols[3], cols[4], Integer.valueOf(cols[2]), Integer.valueOf(cols[1]));
            }
        }

        static class OrderStatusReader extends CsvReader<OrdinanceStatus> {

            public OrderStatusReader(Resource resource) {
                super(resource);
            }

            @Override
            protected OrdinanceStatus csvLineTransform(String line) {
                String[] cols = line.split(";");
                return new OrdinanceStatus(Integer.valueOf(cols[0]), cols[1], OrdinanceStatus.Code.valueOf(cols[2]));
            }
        }

        @Slf4j
        static class CreditOrgReader extends CsvReader<CreditOrganization> {

            public CreditOrgReader(Resource resource) {
                super(resource);
            }

            @Override
            protected CreditOrganization csvLineTransform(String line) {
                String[] cols = line.split(";");
                return new CreditOrganization(Integer.valueOf(cols[0]), cols[3], cols[2]+" "+cols[1]);
            }
        }
    }

    static class CalcIdGenerator implements Supplier<Long> {
        private final Random random = new Random();
        @Override
        public Long get() {
            return random.nextLong();
        }
    }
}
