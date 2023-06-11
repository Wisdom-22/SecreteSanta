from bs4 import BeautifulSoup
import re #regular expressions

with open("index.html", "r") as f:
	doc = BeautifulSoup(f, "html.parser")
#.find gives us the first result of what we searched for
#.find_all gives us all the result we searched for

#result = doc.find("option") #returns just the first result
#access and modify attributes
tag = doc.find("option")
tag['value'] = 'new value'
tag['selected'] = 'false'
#add attributes
tag['color'] = "blue"
#to see all the attributes
print(tag.attrs)
#search for multiple tag names
tags = doc.find_all(["p", "div", "li"])
#search using a combination of search tags
tags = doc.find_all(["option"], text="Undergraduate")
#search using specific values
tags = doc.find_all(["option"], text="Undergraduate", value="undergraduate")
#search using a class name. use the class like this class_
tags = doc.find_all(class_="btn-item")
#using regualr expressions to search. searching for values after a dollar sign($)
tags = doc.find_all(text=re.compile("\$.*"))
for tag in tags:
    print(tag.strip())
#limit the number of result you get when you use find_all
tags = doc.find_all(text=re.compile("\$.*"), limit=1) #limits result to 1
for tag in tags:
    print(tag.strip())
#how to save modificatiosn made to the document
with open("index.html", "r") as f:
    doc = BeautifulSoup(f, "html.parser")
    
tags = doc.find_all(text=re.compile("\$.*"), type="text")
for tag in tags:
    tag['placeholder'] = "I changed you!"
    
with open("changed.html", "w") as file:
    file.write(str(doc))