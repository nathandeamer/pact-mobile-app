## Slack

### Incoming-Webhooks
Create a new emoji in slack for `:pactflow:`

#### New contract published:
```
pact-broker create-webhook \
  'https://hooks.slack.com/services/[WEBHOOK_ID]' \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"icon_emoji":":pactflow:","username":"pactflow","blocks":[{"type":"header","text":{"type":"plain_text","text":"PACT contract published","emoji":true}},{"type":"section","text":{"type":"mrkdwn","text":"New pact contract or version published:\n*<${pactbroker.pactUrl}|${pactbroker.consumerName}/${pactbroker.providerName}>*"}},{"type":"section","fields":[{"type":"mrkdwn","text":"*Consumer:*\n${pactbroker.consumerName}"},{"type":"mrkdwn","text":"*Consumer Version:*\n${pactbroker.consumerVersionNumber}"},{"type":"mrkdwn","text":"*Consumer Tags:*\n${pactbroker.consumerVersionTags}"},{"type":"mrkdwn","text":"*Consumer Branch:*\n${pactbroker.consumerVersionBranch}"}]},{"type":"section","fields":[{"type":"mrkdwn","text":"*Provider:*\n${pactbroker.providerName}"},{"type":"mrkdwn","text":"*Provider Version:*\n${pactbroker.providerVersionNumber}"},{"type":"mrkdwn","text":"*Provider Tags:*\n${pactbroker.providerVersionTags}"},{"type":"mrkdwn","text":"*Provider Branch:*\n${pactbroker.providerVersionBranch}"}]},{"type":"actions","elements":[{"type":"button","text":{"type":"plain_text","emoji":true,"text":"Contract Details"},"url":"${pactbroker.pactUrl}"}]}]}' \
  --description "Slack - contract-published" \
  --contract-published 
 ```  

![Pact Published](pact-published-slack.png "Pact Published")

#### Provider Verification Published:
```
pact-broker create-webhook \
  'https://hooks.slack.com/services/[WEBHOOK_ID]' \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"icon_emoji":":pactflow:","username":"pactflow","blocks":[{"type":"header","text":{"type":"plain_text","text":"[${pactbroker.githubVerificationStatus}] - PACT Verification","emoji":true}},{"type":"section","text":{"type":"mrkdwn","text":"Pact Verification published for:\n*<${pactbroker.verificationResultUrl}| ${pactbroker.consumerName}/${pactbroker.providerName}>*"}},{"type":"section","fields":[{"type":"mrkdwn","text":"*RESULT:*\n${pactbroker.githubVerificationStatus}"}]},{"type":"section","fields":[{"type":"mrkdwn","text":"*Consumer:*\n${pactbroker.consumerName}"},{"type":"mrkdwn","text":"*Consumer Version:*\n${pactbroker.consumerVersionNumber}"},{"type":"mrkdwn","text":"*Consumer Tags:*\n${pactbroker.consumerVersionTags}"},{"type":"mrkdwn","text":"*Consumer Branch:*\n${pactbroker.consumerVersionBranch}"}]},{"type":"section","fields":[{"type":"mrkdwn","text":"*Provider:*\n${pactbroker.providerName}"},{"type":"mrkdwn","text":"*Provider Version:*\n${pactbroker.providerVersionNumber}"},{"type":"mrkdwn","text":"*Provider Tags:*\n${pactbroker.providerVersionTags}"},{"type":"mrkdwn","text":"*Provider Branch:*\n${pactbroker.providerVersionBranch}"}]},{"type":"actions","elements":[{"type":"button","text":{"type":"plain_text","emoji":true,"text":"Verification Details"},"url":"${pactbroker.verificationResultUrl}"}]}]}' \
  --description "Slack - provider-verification-published " \
  --provider-verification-published 
 ```  

![Pact Verification](pact-verification-slack.png "Pact Verification")

#### FAILED Provided Verification Published:
**TODO**