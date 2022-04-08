### Trigger provider build when - VERIFICATION Required
```
pact-broker create-webhook \
  'https://circleci.com/api/v1.1/project/github/nathandeamer/${pactbroker.providerName}/build?circle-token=${user.CircleCIToken}' \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"branch": "main"}' \
  --description "VERIFICATION REQUIRED - Trigger Provider circle build" \
  --contract-requiring-verification-published
 ```

