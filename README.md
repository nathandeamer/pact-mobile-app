Set up:
1. Install the pact cli
2. Get a pactflow account
3. Set environment variables: ND_PACT_BROKER_URL and ND_PACT_BROKER_TOKEN

Steps:
1. make compile
2. make pact-consumer - Runs the pact consumer tests and publishes the contract to the broker
3. make ENV=dev pact-can-i-deploy - Checks if the provider has verified the contract on dev. 
