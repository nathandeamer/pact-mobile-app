## Supporting multiple versions

There can be multiple versions of mobile applications (consumers) out in the wild where we need the provider to continue supporting each version/contract

### How:
1. Record a releae when a mobile app is pushed into the app store for customers:  
```shell
pact-broker record-release --pacticipant=$(basename `git rev-parse --show-toplevel`) --version=$(git rev-parse HEAD) --environment=prod
```

2. When the provider tests run - it will verify ALL releases to confirm the contract is still met for each of them.

3. When a mobile app version is no longer supporter we need to remember to deprecate that version so that provider no longer tries to verify it.
```shell
pact-broker record-support-ended --pacticipant=$(basename `git rev-parse --show-toplevel`) --version=<git-sha-of-previous-release> --environment=prod
```
