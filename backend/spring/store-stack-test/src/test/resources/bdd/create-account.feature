Feature: Creer des comptes pour les specialistes
    L_application d_administration doit pouvoir permettre de creer des comptes pour les specialistes de la clinique.

    Background:
        Given l_admin suivant existe
        | meli|
    
    Scenario Outline: Un admin donne cree un compte pour un specialiste avec de bonnes donnees

        Given un admin "<intituleAdmin>" connecte et identifie par le systeme
        When l_admin tente de creer un compte specialiste avec de bonnes donnees
        Then le "<login>" et le "<password>" sont generes pour ce specialiste.
        
     Examples:
            | intituleAdmin | login  | password |                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
            | meli          | dan    | 12345    |
            | meli          | brice  | 12345    |

    Scenario Outline: Creation d_un compte avec une adresse mail deja utilisee
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>"
        And que l_adresse mail est deja utilisee
        Then le systeme ne l_enregistre pas et envoie un message indiquant l_adresse est deja utilisee
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli         | Momo    | Dani   | +237691971542       | dan   | dani@gmail.com   | 12345        | 
        |meli         | Ndadji  | Brice  | +237699585778      | brice | dani@gmail.com    | 12345       |

    Scenario Outline: Creation d_un compte avec une adresse mail mal formatee
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>"
        And que l_adresse mail est mal formatee
        Then le systeme ne l_enregistre pas et envoie un message indiquant l_adresse est mal formatee
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli         | Momo    | Dani   | +237691971542       | dan   | dani@gmail.   | 12345        | 
        |meli         | Ndadji  | Brice  | +237699585778      | brice | brice@.com   | 12345       |

    Scenario Outline: Creation d_un compte avec un nom non fourni
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>" , "<Confirmer mot de passe>"
        And que le nom est non fourni
        Then le systeme ne l_enregistre pas et envoie un message indiquant que le nom est non fourni
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli          |    | Dani   | +237691971542       | dan   | dani@gmail.com   | 12345        | 
     
    Scenario Outline: Creation d_un compte avec un prenom non fourni
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>" , "<Confirmer mot de passe>"
        And que le prenom est non fourni
        Then le systeme ne l_enregistre pas et envoie un message indiquant que le prenom est non fourni
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli          |   Momo |    | +237691971542       | dan   | dani@gmail.com   | 12345        | 

   Scenario Outline: Creation d_un compte avec un numero mal formate
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>"
        And que le numero est mal formate
        Then le systeme ne l_enregistre pas et envoie un message indiquant le numero mal formate
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli         | Momo    | Dani   | +691971542       | dan   | dani@gmail.com  | 12345        | 
        |meli         | Ndadji  | Brice  | +237699585     | brice | brice@gmail.com   | 12345       |

    Scenario Outline: Creation d_un compte avec un numero non fourni
        Given un admin "<intituleAdmin>" qui tente de creer un compte avec les informations suivantes: "<Nom>", "<Prenom>" , "<Numero de téléphone>" , "<Login>" , "<Email>" , "<Mot de passe>"
        And que le numero est non fourni
        Then le systeme ne l_enregistre pas et envoie un message indiquant le numero non fourni
        Examples: 
        |intituleAdmin| Nom     | Prenom | Numero de téléphone | Login | Email             | Mot de passe |
        |meli         | Momo    | Dani   |      | dan   | dani@gmail.com  | 12345        | 

    Scenario Outline: modifier correctement les informations d_un specialiste
    Given un admin "<intituleAdmin>" connecte et indentifie par le systeme
    When un admin tente de modifier un compte en renseignant correctement tous les champs
    Then le compte est mis a jour.