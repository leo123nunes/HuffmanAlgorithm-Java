package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanCode {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("BEM VINDO AO COMPACTADOR DE ARQUIVOS DE TEXTO");
		System.out.println("-----------------------");
		System.out.println("Digite o caminho de origem do arquivo .txt para realizar a compactação: ");
		String sourcePath = sc.next();
		System.out.println("-----------------------");
		System.out.println("Digite o caminho de destino para o arquivo .txt compactado: ");
		String destinyPath = sc.next();
		System.out.println("-----------------------");

		long startCompressTime = System.currentTimeMillis();

		try {
			BufferedReader br = new BufferedReader(new FileReader(sourcePath));
			StringBuffer textOfFilee = new StringBuffer();
			textOfFilee.append(br.readLine());
			String line = "";

			// Escrevendo na String textOfFile os caracteres do arquivo

			while (line != null) {
				textOfFilee.append(line);
				line = br.readLine();
				textOfFilee = textOfFilee.append("\n");
			}
			br.close();

			System.gc();
			Map map = new Map(255);
			for (int c = 0; c < textOfFilee.length(); c++) {
				map.addElement(textOfFilee.charAt(c));
			}

			System.out.println();
			System.out.println("FOLHAS DA ARVORE BINARIA");
			map.printElements();
			System.out.println("-----------------------");

			// Construindo a arvore binária com os caracteres e frequencias adicionados no
			// map

			HuffmanTree binaryTree = buildTree(map.getKeys(), map.getFrequencies());

			MapDictionary dictionary = new MapDictionary(255);

			System.out.println("-----------------------");
			System.out.println("TABELA DE CÓDIGOS");
			System.out.println("CARACTERE\tFREQUÊNCIA\tCODIFICAÇÃO");
			printCodes(binaryTree, new StringBuffer(), dictionary);
			System.out.println();

			// Armazenando a decodificação do texto na string codedText
			BufferedBitWriter bb = new BufferedBitWriter(destinyPath);
			long quantidadeBits = 0;
			StringBuffer codedText = new StringBuffer();
			for (int i = 0; i < textOfFilee.length(); i++) {
				codedText.append(dictionary.searchForCode(textOfFilee.charAt(i)));
				for (int u = 0; u < codedText.length(); u++) {
					if (codedText.charAt(u) == '1') {
						bb.writeBit(1);
						quantidadeBits++;
					} else {
						bb.writeBit(0);
						quantidadeBits++;
					}
				}
				codedText = new StringBuffer();
			}
			
			bb.close();

			System.out.println("Tamanho do arquivo compactado: " + quantidadeBits / 8 + " byte(s).");
			System.out.println("Tempo total da compactação: " + (System.currentTimeMillis() - startCompressTime) * 0.001
					+ " segundo(s).");
			System.out.println("-----------------------");
			System.out.println("Arquivo compactado! Diretório: '" + destinyPath + "' Deseja descompacta-lo? (s/n) ");
			String answear = sc.next();
			System.out.println("-----------------------");
			System.out.println("Digite o caminho de destino para a descompactação:");
			String destinyDecodedFile = sc.next();
			long startDecodedTime = System.currentTimeMillis();

			if (answear.charAt(0) == 's') {
				try (BufferedWriter bw2 = new BufferedWriter(new FileWriter(destinyDecodedFile))) {
					
					// Abrindo o arquivo codificado
					BufferedBitReader br2 = new BufferedBitReader(destinyPath);

					// Lendo os bits do arquivo e colocando em uma string
					StringBuilder codedString = new StringBuilder();
					StringBuffer decodedString = new StringBuffer();
					int bit;
					bit = br2.readBit();
					while (bit == 1 || bit == 0) {
						codedString.append(bit);
						char x = '\0';
						if (codedString.length() >= dictionary.getMinBits()) {
							x = dictionary.getKeyFromCode(codedString.toString());
						}
						if (x != '\0') {
							bw2.write(x);
							codedString = new StringBuilder();
						}
						bit = br2.readBit();
					}
					// Escrevendo no arquivo de destino o a String contendo o texto decodificado

					bw2.close();

					System.out.println("Tempo total da descompactação: "
							+ (System.currentTimeMillis() - startDecodedTime) * 0.001 + " segundo(s).");
					System.out.println("Arquivo descompactado com sucesso! Diretório: '" + destinyDecodedFile + "'");
				} catch (IOException e) {
					System.out.println("Caminho de destino inválido. Por gentileza reinicie a aplicação.");
					System.out.println("Fim da aplicação.");
				}
			} else {

			}

			System.out.println("Fim da aplicação.");

		} catch (IOException e) {
			System.out.println("Caminho de origem ou destino inválido. Por gentileza reinicie a aplicação.");
			System.out.println("Fim da aplicação.");
		} catch (NullPointerException e) {
			System.out.println("O arquivo de texto do caminho de origem está em branco.");
			System.out.println("Fim da aplicação.");
		}
	}

	// funcao responsavel por retornar a arvore binaria do texto
	public static HuffmanTree buildTree(char[] caractere, int[] frequency) {

		Queue orderedQueue = new Queue(caractere.length);

		System.out.println("MONTAGEM DOS NÓS DA ÁRVORE");
		for (int i = 0; i < caractere.length; i++) {
			if (frequency[i] > 0) {
				orderedQueue.addElement(new HuffmanLeaf(frequency[i], caractere[i]));
			}
		}

		while (orderedQueue.getElements() > 1) {
			HuffmanTree left = orderedQueue.removeElement();
			HuffmanTree right = orderedQueue.removeElement();
			orderedQueue.addElement(new HuffmanNode(left, right));
		}

		System.out.println("^^^ RAÍZ DA ÁRVORE");
		System.out.println();
		return orderedQueue.removeElement();
	}

	// funcao responsavel por retornar a codificação do texto
	public static String encode(HuffmanTree binaryTree, char[] caractere) throws IOException {
		String codedText = "";
		int bytesOriginFile = 0;

		for (char c : caractere) {

			codedText += (getCodes(binaryTree, new StringBuffer(), c));

			if (c == '\n') {
				bytesOriginFile += 2;
			} else if (c >= 32 && c <= 127) {
				bytesOriginFile += 1;
			} else {
				bytesOriginFile += 2;
			}

			System.out.print(c);

		}

		System.out.println("-----------------------");
		System.out.println("Tamanho do arquivo de origem: " + bytesOriginFile + " byte(s).");
		return codedText;
	}

	// funcao responsavel por retornar a decodificação texto
	public static String decode(HuffmanTree binaryTree, String codedText) {
		String decodedText = "";
		HuffmanNode node = (HuffmanNode) binaryTree;

		// vai percorrer os nós até encontrar uma folha na arvore
		for (char code : codedText.toCharArray()) {
			if (code == '0') {
				if (node.left instanceof HuffmanLeaf) {
					decodedText += ((HuffmanLeaf) node.left).value;
					node = (HuffmanNode) binaryTree; // Retorna para a Raíz da árvore
				} else {
					node = (HuffmanNode) node.left; // Continua percorrendo a árvore pelo lado Esquerdo
				}
			} else if (code == '1') {
				if (node.right instanceof HuffmanLeaf) {
					decodedText += ((HuffmanLeaf) node.right).value;
					node = (HuffmanNode) binaryTree; // Retorna para a Raíz da árvore
				} else {
					node = (HuffmanNode) node.right; // Continua percorrendo a árvore pelo lado Direito
				}
			}
		}
		return decodedText;
	}

	// funcao responsavel por printar os caracteres do texto e seus respectivos
	// códigos
	public static void printCodes(HuffmanTree binaryTree, StringBuffer code, MapDictionary dictionary) {
		if (binaryTree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf) binaryTree;
			if (leaf.value == '\n') {
				System.out.println("Quebra de Linha\t" + leaf.frequency + "\t\t" + code);
				dictionary.addElement(leaf.value, code.toString());
			} else if (leaf.value == ' ') {
				System.out.println("Espaço\t\t" + leaf.frequency + "\t\t" + code);
				dictionary.addElement(leaf.value, code.toString());
			} else {
				System.out.println(leaf.value + "\t\t" + leaf.frequency + "\t\t" + code);
				dictionary.addElement(leaf.value, code.toString());
			}
		} else if (binaryTree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode) binaryTree;

			// utiliza da recursividade para percorrer a árvore, aqui começa a percorrer
			// para a esquerda, atribuindo
			// o caractere 0 na string
			code.append('0');
			printCodes(node.left, code, dictionary);
			code.deleteCharAt(code.length() - 1);

			// aqui começa a percorrer para a direita, atribuindo o caractere 1 na string
			code.append('1');
			printCodes(node.right, code, dictionary);
			code.deleteCharAt(code.length() - 1);

		}
	}

	// funcao responsavel por retornar o codigo do caractere procurado do texto
	public static String getCodes(HuffmanTree binaryTree, StringBuffer code, char searchedCaractere) {
		if (binaryTree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf) binaryTree;

			if (leaf.value == searchedCaractere) {
				return code.toString();
			}

		} else if (binaryTree instanceof HuffmanNode) {
			HuffmanNode node = (HuffmanNode) binaryTree;

			code.append('0');
			String left = getCodes(node.left, code, searchedCaractere);
			code.deleteCharAt(code.length() - 1);

			code.append('1');
			String right = getCodes(node.right, code, searchedCaractere);
			code.deleteCharAt(code.length() - 1);

			if (left == null)
				return right;
			else
				return left;
		}
		return null;
	}
}
