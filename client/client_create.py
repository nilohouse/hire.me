import requests

result = requests.put('http://localhost/create?url=http://reddit.org')

print(result.text)

result = requests.put('http://localhost/create?url=http://4chan.org')

print(result.text)

result = requests.put('http://localhost/create?url=http://g1.globo.com')

print(result.text)

result = requests.put('http://localhost/create?url=http://ahoyberlin.com.br/home/&CUSTOM_ALIAS=ahoy')

print(result.text)

result = requests.put('http://localhost/create?url=http://ufc.com/&CUSTOM_ALIAS=ufc')

print(result.text)

result = requests.put('http://localhost/create?url=http://facebook.com/&CUSTOM_ALIAS=face')

print(result.text)

result = requests.put('http://localhost/create?url=http://twitter.com/&CUSTOM_ALIAS=twitter')

print(result.text)


