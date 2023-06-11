from bs4 import BeautifulSoup
import requests


url = "https://www.newegg.ca/Appliances/Store/ID-13"
result = requests.get(url).text
doc = BeautifulSoup(result, "html.parser")

bodyContent = doc.find_all(class_="item-info")
bodyPrices = doc.find_all(class_="price-current")
bodyImages = doc.find_all(class_="item-img")

mainBody = {}
bodyContents = []
pricesContent = []
imagesContent = []

description = ""
price = ""
point = ""
count = 0

for tag in bodyContent:
#the tag should be truncated to only have the text you need
    #print(tag.find_all("a"))
    str1 = ''.join(str(tag.find_all("a")))
    #print()
    #start: int = str1.find('<span class="item-open-box-italic"></span>')
    #end: int = str1.find('</a>, <a href="https://kb.newegg.com/')
    position1: int = str1.index('<span class="item-open-box-italic"></span>')
    position2: int = str1.index('</a>, <a href="https://kb.newegg.com/')
    description = str(str1[position1+42:position2])
    #print(description)
    bodyContents.append(description)
    
    #print()
    
#print("######################")
#print(bodyContents, len(bodyContents))
    
for tag in bodyPrices:
    #print(tag.find_all("strong"))
    #print(tag.find_all("sup"))
    str1 = ''.join(str(tag.find_all("strong")))
    str2 = ''.join(str(tag.find_all("sup")))
    
    position1: int = str1.index('<strong>')
    position2: int = str1.index('</strong>')
    price = str1[position1+8:position2]
    
    position3: int = str2.index('<sup>')
    position4: int = str2.index('</sup>')
    point = str2[position3+5:position4]
    
    #print(price)
    #print(point)
    price = price+''.join(str(point))
    #print(price)
    #print()
    
    pricesContent.append(price)
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
    
#print("######################")
#print(pricesContent, len(pricesContent))


for n in range(len(bodyContents)):
    mainBody[bodyContents[n]] = pricesContent[n]
#    print("Main Body List", dict(zip(part1, part2)))
#print(mainBody)

for key, value in mainBody.items():
    print(key +" : "+ value + " && " + imagesContent[count])
    count = count + 1



    