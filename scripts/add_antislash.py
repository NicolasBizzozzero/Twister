"""Ajoute des antislash avant chaque symbole " dans un fichier HTML.
Ce script sert juste à éviter d'ajouter ces antislash nous-mêmes lorsqu'on veut
utiliser du code HTML dans un fichier javascript.
Pour l'executer, il suffit de tapper la commande :
$ python3 add_antislash.py FICHIER1 FICHIER2 FICHIER3 ...
Les fichiers seront directement modifiés.
"""
from sys import argv


def insert_antislash(string: str, index: int) -> str:
	return string[:index] + '\\' + string[index:] 


def get_all_positions_of_doublequote(string: str) -> list:
	return [pos for pos, char in enumerate(string) if char == '\"']


def get_content_of_file(filepath: str) -> str:
	content = ""
	with open(filepath) as file:
		for line in file:
			content += line
	return content


def replace_content(filepath: str, content: str) -> None:
	with open(filepath, 'w') as file:
		file.write(content)


def main():
	for filename in argv[1:]:
		content = get_content_of_file(filename)
		positions = get_all_positions_of_doublequote(content)
		for index_index, index in enumerate(positions):
			index += 1 * index_index	#TODO: Peut etre virer ce 1 ?
			content = insert_antislash(content, index)
		replace_content(filename, content)


if __name__ == "__main__":
	main()
