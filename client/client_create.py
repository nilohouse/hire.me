import requests

result = requests.put('http://localhost/create?url=http://reddit.org')

print(result.text)

result = requests.put('http://localhost/create?url=http://ahoyberlin.com.br/home/&CUSTOM_ALIAS=ahoy')

print(result.text)