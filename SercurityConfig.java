/* 경로 프로젝트/src/main/java/com.example.demo/config */

//시큐리티 핵심 기능들을 컨트롤 하는 클래스
@Configuration
@EnableWebSecurity
public class SercurityConfig {
		
	@Bean
	public BCryptPasswordEncoder encodePwd() { //패스워드를 암호화 해주는 시큐리티 매서드 중 하나 이거해야 패스워드가 암호화됨
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain mySecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable();

		 http
		.authorizeHttpRequests()
		.requestMatchers("/main/**").authenticated() //해당 url주소로 들어오면 인증이 필요해
		.requestMatchers("/manager/**").access(new WebExpressionAuthorizationManager("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')"))//manager 쪽으로 들어올려면 어드민 or 매니저 권한이 있는 사람만 들어와 / hasAnyRole 은 여러권한
		.requestMatchers("/admin/**").access(new WebExpressionAuthorizationManager("hasRole('ROLE_ADMIN')")) //admin은 어드민 권한 있는 사람만 들어와 / hasRole은 단일 권한
		//anyRequest()모든 리퀘스트에 대한 / permitAll() 하면 위에 따로 권한 설정한 페이지 외에는 전부 접속 가능 
		//기본 로그인 페이지가 노출되지 않고 바로 접근이 가능 / authenticated() = 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.usernameParameter("userid")
		.passwordParameter("userpw")
		.loginPage("/login") // 기본 로그인 페이지말고 내가 지정한 로그인 페이지로 이동 loginPage("매핑주소") / disable() 로그인 페이지로 이동 x
		.loginProcessingUrl("/loginDo") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행 해준다. *컨트롤러에서 login을 따로 안만들어도 된다.
		.defaultSuccessUrl("/main"); //로그인 성공시 기본적으로 이동하는 url을 설정
		return http.build();
	}
	
}
