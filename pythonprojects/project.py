from bs4 import BeautifulSoup
import requests
import re
import operator

url = "https://www.newegg.ca/"
result = requests.get(url).text
doc = BeautifulSoup(result, "html.parser")

#body = doc.find_all(class_="grid-col radius-m bg-gradient-lightblue col-h-2x") #(["div"], id="shellShocker")#class="goods-container is-reverse is-card is-vertical"
#tags = doc.find_all(string=re.compile("\$.*"), limit=1)
#bodyContent = doc.find_all(class_="grid-col radius-m bg-gradient-lightblue")

#bodyContent = doc.find_all(["img"])
bodyContent = doc.find_all(class_="goods-info")
bodyPrices = doc.find_all(class_="goods-price-current")
mainBody = {}
bodyContents = []
prices = []
#print(bodyContent)

for tag in bodyContent:
    #print(tag.find_all("a")[-1].string)
    str1 = ''.join(str(tag.find_all("a")[-1].string))
    description = str1
    bodyContents.append(description)
    #print(description)
    #parent = bodyContent[0].parent
    #link = parent.find(["a"], title="View Details")
    #img = link.find("img")
    #print(link)
    #print()
    
#print("#####################")
#print(bodyContents)
   
    
for tag in bodyPrices:
    #print(tag.find_all("strong"))
    str1 = ''.join(str(tag.find_all("strong")))
    position1: int = str1.index('<strong>')
    position2: int = str1.index('</strong>')
    detailsPrices = str(str1[position1+8:position2])
    #print(detailsPrices)
    prices.append(detailsPrices)
    #print(detailsPrices)
    #print()
        
#print("##################################################")    
#print(prices)

for n in range(len(bodyContents)):
    if(bodyContents[n] != 'None' and bodyContents[n] != 'learn more'):
        mainBody[prices[n]] = bodyContents[n]
        #print(bodyContents[n], " : ", prices[n])
#    print()
#print("Main Body List", dict(zip(part1, part2)))
#print(mainBody)

for key, value in mainBody.items():
    print(key +" : "+ value)
