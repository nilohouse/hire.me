import requests

result = requests.get('http://localhost/retrieve/ahoy')

print(result.text)
