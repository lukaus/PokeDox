#Python program to pull pokeman data from pokeapi and generate source data for PokeDox
import requests
import json
import urllib.request

#Set this to the highest dexvalue
MAX_DEXNUM = 802

base_data_url = "https://pokeapi.co/api/v2/"
base_img_url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"

def pull_dexnum(first, last=None):
	if last == None:
		last = first
	for x in range(first, last + 1):
		url = base_data_url + "pokemon/" + str(x) + "/"
		response = requests.get(url)
		response.raise_for_status()

		data = json.loads(response.text)

		filename = "./data/" + str(x).zfill(3) + "-" + data["name"] + ".json"
		outfile = open(filename, 'w')
		json.dump(data, outfile, indent=2)
		outfile.close()
		print("Downloaded: {}".format(data["name"]))

def pull_sprite(first, last=None):
	if last == None:
		last = first
	for x in range(first, last + 1):
		url = base_img_url + str(x) + ".png"
		filename = "./sprites/" + str(x).zfill(3) + ".png"
		urllib.request.urlretrieve(url, filename)

		print("Downloaded: {}".format(x))

#Pokedox format:
# NationalDex, JhotoDex, HoennDex, SinnohDex, UnovaDex, KalosDex, <AlolanDex>, name, type1, type2, isCaught, isSeen, isWant, isTrade, multiples, evofamily

# Read dex number and convert it from json to PokeDox readable
def json_to_dox(dex_number):
	return

#pull_dexnum(1, MAX_DEXNUM)
#pull_sprite(1, MAX_DEXNUM)