from bs4 import BeautifulSoup
import requests
import re
import operator

url = "https://www.newegg.ca/Office-Solutions/Store/ID-133"
result = requests.get(url).text
doc = BeautifulSoup(result, "html.parser")

bodyContent = doc.find_all(class_="item-info")
bodyPrices = doc.find_all(class_="price-current")
bodyImages = doc.find_all(class_="item-img")

mainBody = {}
bodyContents = []
prices = []
imagesContent = []
count = 0

for tag in bodyContent:
    #print(tag.find_all("a"))
    str1 = ''.join(str(tag.find_all("a")))
    position1: int = str1.index('<span class="item-open-box-italic"></span>')
    position2: int = str1.index('<a href="https:')
    description = str(str1[position1+42:position2-6])
    #print(description)
    bodyContents.append(description)
    #print()


for tag in bodyPrices:
    #print(tag.find_all("strong"))
    str1 = ''.join(str(tag.find_all("strong")))
    str2 = ''.join(str(tag.find_all("sup")))
    position1: int = str1.index('>')
    position2: int = str1.index('</')
    
    position3: int = str1.index('>')
    position4: int = str1.index('<')
    price = str(str1[position1+1:position2])
    point = str(str2[position3-2:position4-8])
    #print(price)
    #print(point)
    price = price+''.join(str(point))
    #print(price)
    prices.append(price)
    #print()
    
for tag in bodyImages:
    #print(tag.find_all("img"))
    str1 = ''.join(str(tag.find_all("img")))
    
    position1: int = str1.index('src=')
    position2: int = str1.index('title')
    image = str1[position1+5:position2-2]
    
    imagesContent.append(image)
        
    #print(image)
    #print()
    
for n in range(len(bodyContents)):
    if(bodyContents[n] != 'None' and bodyContents[n] != 'learn more'):
        mainBody[bodyContents[n]] = prices[n]
        #print(bodyContents[n], " : ", prices[n])
    #print()
#print("Main Body List", dict(zip(part1, part2)))
#print(mainBody)
for key, value in mainBody.items():
    print(key +" : "+ value + " && " + imagesContent[count])
    count = count + 1
   