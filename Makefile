SHELL:=/bin/bash

GITHUB_COMMIT_HASH?=$(shell git rev-parse --verify HEAD)

ENV?=dev

compile:
	@./gradlew clean build

pact-consumer:
	@./gradlew clean test
	@pact-broker publish build/pacts --consumer-app-version=${GITHUB_COMMIT_HASH} --broker-base-url=${ND_PACT_BROKER_URL} --broker-token=${ND_PACT_BROKER_TOKEN}

pact-can-i-deploy:
	@pact-broker can-i-deploy --pacticipant mobileapp --version ${GITHUB_COMMIT_HASH} --broker-base-url=${ND_PACT_BROKER_URL} --broker-token=${ND_PACT_BROKER_TOKEN} --to ${ENV}
