import java.util.*;
import java.io.*;
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader; 

class PenduGame{
	
	public static int NB_errorNbS_MAX=10; //c'est plut�t compr�hensible sans commentaire :/
	
	public static void main (String args[]){
		
		
		int i;
		
		String findEmAll[] = {"ablation","kuebiko","interminable","relique","rubatosis","programme","virus","explosion","monachopsis","pendu","mauerbauertraurigkeit","empaler","sonders","ambedo"}; //mots a trouver (oui ils existent tous vraiment)
		Random rand=new Random(); //pour utiliser des nombres random.
		int randomNb=rand.nextInt(findEmAll.length);
		String secretWord=findEmAll[randomNb]; //mot qui a �t� tir� au hasard dans la liste
		int wordLen=secretWord.length();
				
		char tabSecretWord[]; //tableau de m�me taille que le mot a trouver
		tabSecretWord=new char [wordLen];
		
		boolean foundWord=false; //savoir si le joueur a trouv� le mot ou pas
		char letter; //lettre saisie
		int goodLettersNb=0; //nb de bonnes lettres trouv�es.
		boolean goodLetter=false; //Nous permettra de savoir si l'utilisateur a entr�e une lettre qui se trouve dans le mot ou non?
		
		boolean difference;//savoir si la lettre saisie est dans le mot
		int errorNb=0;
		boolean sameWord=false;  //le joueur a entr� une bonne lettre qui est d�j� sortie
		boolean sameWord2=false; //le joueur a entr� une lettre fausse mais qu'il a dej� �t� saisie avant
		char verifSaisie[];//Ce tableau contiendra toutes les lettre fausses saisie par l'utilisateur, sa taille est �gal au nombre d'errorNb.
		
		int k = 0;//Permettra de d�caler les cases du tableau de lettres fausse de 1 a chaque errorNb.
		
		verifSaisie=new char [NB_errorNbS_MAX];
		
		//-------------------------------------------------------------------------------------------------
		//jolie ent�te
		System.out.println("*********************************");
		System.out.println("Tiens mais �a ressemble au jeu du pendu*");
		System.out.println("*********************************");
		System.out.println("");
		
		System.out.print("Bon je suppose qu'il faut trouver un mot l�...");
		System.out.print("ok, alors DU COUP �a commence par un " + secretWord.charAt(0));
		
		tabSecretWord[0] = secretWord.charAt(0);
		for (i=1;i<tabSecretWord.length;i++){
			
			if (i==tabSecretWord.length-1){ //On met la derni�re lettre du mot dans la derni�re case
				tabSecretWord[i]=secretWord.charAt(i);
			}
			else{
				tabSecretWord[i]='-'; //Pour le reste entre on met des tirets
			}
			System.out.print(tabSecretWord[i]);
		}
		
		System.out.println("");
		System.out.println("");
		System.out.println("-----------------------------------");
		System.out.println("");
		
		do{
			goodLettersNb=0; //bon logiquement au d�but aucune lettre est bonne vu qu'il n'a rien rentr� comme lettre
			goodLetter=false; //pareil
			
			do{ //tant que le joueur rentre la m�me lettre comme un con :
				if ((sameWord!=false) || (sameWord2!=false)){	
				
					System.out.println("-------------------------------------");
					System.out.println("FAUX !! r��ssaie mais remets pas la m�me stp");	
					System.out.println("-------------------------------------");
					System.out.println("");
					System.out.print("RAPPEL: le mot a chercher est: ");
				
					for (i=0;i<tabSecretWord.length;i++){ //On r�affiche le mot a chercher
						System.out.print(tabSecretWord[i]);
						System.out.println("");
					}
				}
						
				//nouvel essai on r�initialise les variables
				sameWord=false;
				sameWord2=false;
				difference=true; 

				System.out.println("Alors? quelle lettre tu proposes? ");
				
				BufferedReader reader = new BufferedReader( 
			            new InputStreamReader(System.in));			   
		        letter = reader.readLine().toLowerCase().charAt(0); //on convertit cette lettre en minuscule

				for (i=0;i<NB_errorNbS_MAX;i++){ //on parcours le tab des lettres saisies qui sont fausses (les erreurs) et si la lettre est dedans ben.. il a une nouvelle erreur
					if (verifSaisie[i]==letter){
						sameWord2=true;
					}
				}
				
				for (i=1;i<tabSecretWord.length-1;i++){//si le joueur a saisie une lettre qui est dans le mot ou elle se trouve dans le tableau des lettres fausses d�j� saisie alors $difference devient faux. L'utilisateur a donc juste.
				
					if ((letter==secretWord.charAt(i)) || (sameWord2==true)){
						difference=false;
					}
				}
									
				if (difference==true){ //Si difference est vrai cela signifie que l'utilisateur s'est tromp� on met alors la lettre fausse dans le tableau des lettres fausses VerifSaisie.
					verifSaisie[k]=letter;
					k=k+1; //nb d'essai diminue d'1 du coup
				}

				for (i=1;i<tabSecretWord.length-1;i++){ //On regarde si la lettre saisie n'est pas d�j� visible � l'�cran.
				
					if (letter==tabSecretWord[i]){
						sameWord=true;
					}
				}
			}	
		
			while((sameWord!=false) || (sameWord2!=false));
				System.out.println(""); //On utilise cette boucle pour v�rifier si la lettre saisie correspond bien avec une des lettre dans le mot.
						
				for (i=1;i<tabSecretWord.length-1;i++){
					if (letter==secretWord.charAt(i)){
						tabSecretWord[i]=letter; //si il y a correspondance alors on remplace le tiret par la lettre.
						goodLetter=true;
					}	
				}
			
				if (goodLetter!=true){ //si il s'est tromp� alors on lui ajoute errorNb +1
					errorNb=errorNb+1;
						
					System.out.println("Plus que "+(NB_errorNbS_MAX-errorNb)+" chance(s) et apr�s t'es mort.");
					System.out.println("");
				}
				
				if (errorNb!=NB_errorNbS_MAX){ //si il est pas encore mort	
					for (i=1;i<tabSecretWord.length-1;i++){

						if (tabSecretWord[i]==secretWord.charAt(i)){ //On compte le nombre de bonnes lettres trouv�es
							goodLettersNb=goodLettersNb+1;
						}	
					
						if (goodLettersNb==secretWord.length()-2){ //Si le nombre de bonnes lettres correspond au nombre de tirets dans le mot alors le joueur a gagn�.
							foundWord=true;
							System.out.println("Bravo fr�rot t'as trouv� le mot");
						}
					}

					if (foundWord!=true){

						System.out.println("Vous avez trouve en tout "+goodLettersNb+" lettres."); //Si le mot n'est pas trouv� on affiche le nombre de lettres trouv�es en tout
						System.out.println("");
						System.out.print("Maintenant le mot devient: ");
						
						for (i=0;i<tabSecretWord.length;i++){
							
							System.out.print(tabSecretWord[i]);
						}
						System.out.println("");
						System.out.println("");
					}
					System.out.println("-----------------------------------");
				}
			}
		
			while ((foundWord==false) && (errorNb!=NB_errorNbS_MAX))	; // On run les instructions du dessus tant que le joueur n'a pas perdu et/ou tant que le mot n'a pas �t� trouv�.
			
				if (errorNb==NB_errorNbS_MAX){ //si d'errorNb est au max c'est game over 
					System.out.println("-----------------------------------");
					System.out.println("Fin de partie t'es mort fr�rot.");
					System.out.println("Fallait trouver "+secretWord);
				
				}
			}
		}
	}	
}