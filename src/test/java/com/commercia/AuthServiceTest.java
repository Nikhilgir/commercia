package com.commercia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.commercia.entity.User;
import com.commercia.entity.User.Gender;
import com.commercia.exception.SwiggyException;
import com.commercia.repository.AddressesRepository;
import com.commercia.repository.CountryRepository;
import com.commercia.repository.UserRepository;
import com.commercia.request.AddressRequest;
import com.commercia.request.SignupRequest;
import com.commercia.service.AuthService;
import com.commercia.service.serviceImp.AuthServiceImp;

//@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	private UserRepository userRepository = Mockito.mock(UserRepository.class);
	private AddressesRepository addressesRepository = Mockito.mock(AddressesRepository.class);
	private CountryRepository countryRepository = Mockito.mock(CountryRepository.class);
	private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
	private AuthenticationManager authenticationManager = Mockito.mock(AuthenticationManager.class);
	SignupRequest requestUser;
	User expectedUser;

	// @InjectMocks
	private AuthService authService = new AuthServiceImp(userRepository, addressesRepository, countryRepository,
			passwordEncoder, authenticationManager);

	@BeforeAll
	public static void beforeAll() {
		System.out.println("Testing start...");
	}

	@BeforeEach
	public void beforeEachTest() {
		System.out.println("@Before method");
		requestUser = new SignupRequest();
		requestUser.setFirstName("Nikhil");
		requestUser.setLastName("giri");
		requestUser.setPassword("Nikhil@123");
		requestUser.setEmail("nk822686@gmail.com");
		requestUser.setPhoneNumber(8271181100L);
		requestUser.setGender("MALE");
		requestUser.setDateOfBirth(LocalDate.of(1999, 11, 12));
		List<AddressRequest> addressList = new ArrayList<AddressRequest>();
		AddressRequest ad = new AddressRequest();
		ad.setHouseNo("D46");
		ad.setLandMark("Noida");
		ad.setCity("Siwan");
		ad.setState("Bihar");
		ad.setZipCode(841413);
		addressList.add(ad);
		requestUser.setAddress(addressList);

		expectedUser = new User();
		expectedUser.setFirstName(requestUser.getFirstName());
		expectedUser.setLastName(requestUser.getLastName());
		expectedUser.setPassword(requestUser.getPassword());
		expectedUser.setEmail(requestUser.getEmail());
		expectedUser.setPhoneNumber(requestUser.getPhoneNumber());
		expectedUser.setAge(Period.between(requestUser.getDateOfBirth(), LocalDate.now()).getYears());
		expectedUser.setDateOfBirth(requestUser.getDateOfBirth());
		expectedUser.setGender(Gender.valueOf(requestUser.getGender().toUpperCase()));
	}

	@DisplayName("JUnit test for saveSignupDetails method")
	@Test
	public void saveSignupDetailsTest() {
		
		when(userRepository.save(any(User.class))).thenReturn(expectedUser);
		
		User returnedUser = authService.saveSignupDetails(requestUser);

		assertNotNull(returnedUser);
		assertEquals("Nikhil", returnedUser.getFirstName());
		
		verify(userRepository, times(1)).save(any(User.class));
	}

	@DisplayName("JUnit test for exception must be throw if argument is null")
	@Test
	void registerUser_ShouldHandleNullUser() {
		requestUser = null;
		assertThrows(IllegalArgumentException.class, () -> authService.saveSignupDetails(requestUser));
	}

	@DisplayName("JUnit test for Database exception")
	@Test
    void registerUser_ShouldHandleExceptionFromRepository() {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Database error"));
        assertThrows(RuntimeException.class, () -> authService.saveSignupDetails(requestUser));
    }

	@DisplayName("JUnit test for age>18 exception")
	@Test
	void registerUser_ShouldHandleExceptionForAge() {
		requestUser.setDateOfBirth(LocalDate.of(2012, 12, 10));
		assertThrows(SwiggyException.class, () -> authService.saveSignupDetails(requestUser));
	}

	@AfterAll
	public static void AfterAll() {
		System.out.println("Testing end!");
	}

}
