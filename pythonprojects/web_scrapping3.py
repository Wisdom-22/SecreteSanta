from bs4 import BeautifulSoup
import requests

url = "https://coinmarketcap.com/"
result = requests.get(url).text
doc = BeautifulSoup(result, "html.parser")

tbody = doc.tbody
trs = tbody.contents
# table row 0, next sibling
#print(trs[].next_siblings)

# table row 1, previous sibling
#print(trs[1].previous_siblings)

#next siblings. look at the table rows that come after the first table row
#print(list(trs[0].next_siblings))

#parent of the table row
print(trs[0].parent)

#returns the parent name of the table row
#print(trs[0].parent.name)

#looking at the descendants
print(list(trs[0].descendants))

#looking at the contents
print(list(trs[0].contents))

#looking at the children
print(list(trs[0].children))

#looking for the name and the prices
prices = {}

for tr in trs[:10]:
    name, price = tr.contents[2:4]
    fixed_name = name.p.string
    fixed_price = price.a.string
    
    prices[fixed_name] = fixed_price
print(prices)