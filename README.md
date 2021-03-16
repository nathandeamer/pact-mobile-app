Set up:
1. Install the pact cli
2. Get a pactflow account
3. Set environment variables: ND_PACT_BROKER_URL and ND_PACT_BROKER_TOKEN

Steps:
1. make compile
2. make pact-consumer - Runs the pact consumer tests and publishes the contract to the broker
3. make ENV=dev pact-can-i-deploy - Checks if the provider has verified the contract on dev. 


Genrate network graph:
```
echo "digraph { ranksep=3; ratio=auto; overlap=false; node [  shape = plaintext, fontname = "Helvetica" ];" > latest.dot

curl -H 'Authorization: Bearer '"$ND_PACT_BROKER_TOKEN"'' https://nd.pactflow.io/pacts/latest | jq '.pacts[]._embedded | .consumer.name + "->" + .provider.name' | tr -d '"' |  sed 's/-/_/g' | sed 's/_>/->/g' >> latest.dot; echo "}" >> latest.dot

dot latest.dot -otest.png -Tpng

open test.png
```