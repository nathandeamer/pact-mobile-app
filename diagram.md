## Generate network graph:

1. Install graphviz (`brew install graphviz`)

```
echo "digraph { ranksep=3; ratio=auto; overlap=false; node [  shape = plaintext, fontname = "Helvetica" ];" > latest.dot
curl -H 'Authorization: Bearer '"$PACT_BROKER_TOKEN"'' $PACT_BROKER_BASE_URL/pacts/latest | jq '.pacts[]._embedded | .consumer.name + "->" + .provider.name' | tr -d '"' |  sed 's/-/_/g' | sed 's/_>/->/g' >> latest.dot; echo "}" >> latest.dot
dot latest.dot -opact-network.png -Tpng
open pact-network.png
```  
![Pact Network](pact-network.png "Pact Network")
