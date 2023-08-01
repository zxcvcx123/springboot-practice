@Configuration
@EnableTransactionManagement
@MapperScan(basePackages="com.example.demo.mapper")
public class MybatisConfig {
    
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        
        // mybatis-config.xml 설정
        Resource mybatisConfig = new ClassPathResource("mybatis/mybatis-config.xml");
        sessionFactory.setConfigLocation(mybatisConfig);
        
        // mapper.xml 위치 설정
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] mapperLocations = resolver.getResources("classpath:/mybatis/mapper/*.xml");
        sessionFactory.setMapperLocations(mapperLocations);
        
        return sessionFactory.getObject();
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
