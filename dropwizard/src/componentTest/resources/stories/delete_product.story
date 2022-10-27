Meta: Delete product by Id

Scenario: Successful delete product by Id
Given Have a product
When User call delete product by Id
Then Product should be delete successfully