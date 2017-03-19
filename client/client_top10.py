import requests

result = requests.get('http://localhost/top10')

print(result.text)
