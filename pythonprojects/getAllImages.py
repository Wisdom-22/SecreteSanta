from bs4 import BeautifulSoup
import requests

import urllib.request
from urllib.request import urlopen


url = "https://www.newegg.ca/Gaming-VR/Store/ID-8"
result = requests.get(url).text
doc = BeautifulSoup(result, "html.parser")

bodyImages = doc.find_all(class_="dynamic-module-img")

i = 1
for img in bodyImages:
    image_tag = img.find('img')
    image_src = image_tag.get('src')
    
    if image_src[:1] == '/':
        image = 'https:' + image_src
    else:
        image = image_src
    print(image)
    #file_name = str(i)
    #i+=1
    #image_file = open(file_name + '.jpeg', 'wb')
    #image_file.write(urllib.request.urlopen(image).read())
    #image_file.close()
    