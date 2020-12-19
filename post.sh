#!/bin/bash

PRODUCT_URL=$(curl -s http://localhost:8022/products | jq "._embedded.products[0]._links.self.href" -r)

cat <<EOF > /tmp/training.json
{
  "description": "New Training",
  "title": "Training 9999",
  "product": "$PRODUCT_URL"
}
EOF

curl -v http://localhost:8022/trainings -d @/tmp/training.json -H "Content-type: application/json"

