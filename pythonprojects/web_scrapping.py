from bs4 import BeautifulSoup
import requests

url = "https://www.newegg.ca/asus-vz27ehe-27/p/N82E16824281149?Item=N82E16824281149&cm_sp=Homepage_SS-_-P1_24-281-149-_-04252023"

result = requests.get(url)
doc = BeautifulSoup(result.text, "html.parser")

prices = doc.find_all(string="$")
parent = prices[0].parent
strong = parent.find("strong")
print(strong.string)


#with open("index.html", "r") as f:
#    doc = BeautifulSoup(f, "html.parser")


#print entire document    
#print(doc.prettify())

#print a specific html tag
#tag = doc.title
#print(tag)

#to access the content of the tag
#tag = doc.title 
#print(tag.string)

#to change the contents of a tag
#tag = doc.title 
#tag.string = "hello"

#print(doc)

#how to find all the particular tags
#tags = doc.find_all("p") 
 
#print(tags)

#how to find tags that are in another tag
#tags = doc.find_all("p")[0] #the first tag 
 
#print(tags.find_all("b"))
