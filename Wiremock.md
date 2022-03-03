# PACT Wiremock Consumer example

Uses wiremock to generate consumer contracts

## Setup:
See [pact-mobile-app](https://github.com/nathandeamer/pact-mobile-app)

## Run the wiremock consumer tests:
1. `./gradlew clean test --tests "*WiremockTest"`  
   See generated contracts in [build/pacts](build/pacts)

## Publish the consumer tests to the broker
Use the pact-cli and follow the steps in [pact-mobile-app](https://github.com/nathandeamer/pact-mobile-app)

## Provider side:  
If you want to write a provider side test:
```java
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Provider("pact-order")
@PactBroker(url = "${PACT_BROKER_BASE_URL}", authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
public class OrderProviderPactTest {

    @MockBean
    private OrdersRepository ordersRepository;

    @BeforeEach
    public void setupTestTarget(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8080));
    }

    @BeforeEach
    public void setupProvider() {
        when(ordersRepository.findById(eq(1234))).thenReturn(
                Optional.of(CustomerOrder.builder()
                        .id(1234)
                        .items(Collections.singletonList(
                                CustomerOrder.Item.builder()
                                        .qty(1)
                                        .description("New York City Pass")
                                        .sku("NYC")
                                        .build()))
                        .build()));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
}
```
Notice that the values in the body have to match **EXACTLY**

### Notes:
- Wiremock consumer tests (so far) don't seem as flexible as pact DSL.  
  ❌ Matchers - Provider side needs to use the exact same path and return the exact same data.  
  ❌ State - Doesn't support provider side state.