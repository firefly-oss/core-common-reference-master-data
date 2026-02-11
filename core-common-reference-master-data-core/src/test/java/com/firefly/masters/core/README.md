# Master Data Service Test Suite

This directory contains the test suite for the Common Platform Reference Master Data Service. The tests are organized to validate the functionality of service implementations, ensuring that all CRUD operations and business logic work correctly.

## Test Structure

The test suite follows the same package structure as the main application code, with test classes located in corresponding packages:

```
com.firefly.masters.core.services
├── activity.v1
│   └── ActivityCodeServiceImplTest.java
├── bank.v1
│   └── BankInstitutionCodeServiceImplTest.java
├── branch.v1
│   └── BranchServiceImplTest.java
├── consent.v1
│   └── ConsentCatalogServiceImplTest.java
├── country.v1
│   └── CountryServiceImplTest.java
├── currency.v1
│   └── CurrencyServiceImplTest.java
├── division.v1
│   └── AdministrativeDivisionServiceImplTest.java
├── holiday.v1
│   └── HolidayServiceImplTest.java
├── legal.v1
│   └── LegalFormServiceImplTest.java
├── locale.v1
│   └── LanguageLocaleServiceImplTest.java
├── lookup.v1
│   ├── LookupDomainServiceImplTest.java
│   └── LookupItemServiceImplTest.java
├── relationships.v1
│   └── RelationshipTypeMasterServiceImplTest.java
└── title.v1
    └── TitleMasterServiceImplTest.java
```

## Testing Approach

### Unit Testing

The test suite uses JUnit 5 with Mockito for unit testing. Each service implementation is tested in isolation, with dependencies mocked to ensure true unit testing. The reactive nature of the application is tested using Project Reactor's `StepVerifier`.

### Test Coverage

Each service implementation test covers:

1. **List Operations**: Testing pagination functionality for listing entities
2. **Create Operations**: Testing the creation of new entities
3. **Read Operations**: Testing retrieving entities by ID, including both found and not found scenarios
4. **Update Operations**: Testing updating existing entities, including both found and not found scenarios
5. **Delete Operations**: Testing deleting entities, including both found and not found scenarios
6. **Special Operations**: Testing any service-specific operations (e.g., finding by country ID)

## Test Utilities

### TestPaginationRequest

The `TestPaginationRequest` class in the `com.firefly.masters.core.utils` package extends the application's `PaginationRequest` class to provide testing-specific functionality:

```java
public class TestPaginationRequest extends PaginationRequest {
    private int page;
    private int size;
    
    public TestPaginationRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }
    
    // Getters, setters, and utility methods
    
    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
```

This utility class simplifies testing of paginated queries by providing a convenient way to create pagination requests with specific page and size values.

## Running Tests

### Running All Tests

To run all tests in the test suite, use the following Maven command:

```bash
mvn test -pl core-common-reference-master-data-core
```

### Running Specific Tests

To run a specific test class, use:

```bash
mvn test -pl core-common-reference-master-data-core -Dtest=CountryServiceImplTest
```

To run a specific test method, use:

```bash
mvn test -pl core-common-reference-master-data-core -Dtest=CountryServiceImplTest#createCountry_ShouldReturnCreatedCountry
```

## Test Patterns

### Setup Pattern

Each test class follows a consistent setup pattern:

```java
@ExtendWith(MockitoExtension.class)
public class ServiceImplTest {

    @Mock
    private Repository repository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ServiceImpl service;

    private Entity entity;
    private DTO dto;
    private TestPaginationRequest paginationRequest;

    @BeforeEach
    void setUp() {
        // Initialize test data
    }
    
    // Test methods
}
```

### Test Method Pattern

Each test method follows the Arrange-Act-Assert pattern:

```java
@Test
void methodName_Scenario_ExpectedBehavior() {
    // Arrange - Set up test conditions
    UUID testEntityId = UUID.randomUUID();
    when(repository.findById(any(UUID.class))).thenReturn(Mono.just(entity));
    when(mapper.toDTO(any(Entity.class))).thenReturn(dto);

    // Act - Call the method being tested
    Mono<DTO> result = service.getEntity(testEntityId);

    // Assert - Verify the expected behavior
    StepVerifier.create(result)
            .expectNext(dto)
            .verifyComplete();

    // Verify interactions with mocks
    verify(repository).findById(any(UUID.class));
    verify(mapper).toDTO(any(Entity.class));
}
```

## Extending the Test Suite

When adding new services to the application, follow these steps to create corresponding tests:

1. Create a new test class in the appropriate package
2. Follow the established test patterns
3. Ensure all CRUD operations and business logic are covered
4. Use the existing test utilities where appropriate

## Best Practices

1. **Isolation**: Each test should be independent and not rely on the state from other tests
2. **Mocking**: Mock all dependencies to ensure true unit testing
3. **Naming**: Use descriptive test method names that indicate what is being tested and the expected behavior
4. **Verification**: Verify all interactions with mocked dependencies
5. **Edge Cases**: Test both happy path and edge cases (e.g., not found scenarios)

## Troubleshooting

### Common Issues

1. **NullPointerException in tests**: Ensure all mocked methods are properly stubbed
2. **StepVerifier timeout**: Check that the reactive stream completes properly
3. **Verification errors**: Ensure the expected interactions with mocks match the actual implementation

### Debugging Tips

1. Use `StepVerifier.create(result).log()` to log all signals from the reactive stream
2. Use `Mockito.verify(mock, Mockito.times(n))` to verify the exact number of interactions
3. Check that the test data matches the expectations of the service implementation
