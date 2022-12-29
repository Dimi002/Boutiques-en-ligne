Feature: Se connecter a son compte cree par un admin du systeme 
 Chaque specialiste ayant un compte sur le site doit pouvoir se connecter pour effectuer ses operations
Background:
        Given les comptes suivants existent
        | meli|
        | dan  | 
        | brice|
        |dimi  |
    
Scenario Outline: test de la connexion avec les bons identifiants
        Given un user qui visite la route /login
        When un user s_identifie avec les bons champs "<login>" ou "<email>" et "<password>"
        Then le specialiste est dirige vers la page d_accueil.

        Examples:
                | login  | password | email           |                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
                | dan    | 12345    |dan@gmail.com    |
                | brice  | 12345    |brice@gmail.com  |
                |meli    |1234345   |meli@gmail.com   |
                
Scenario Outline: le champ password contient moins de 5 caracteres
        Given un user qui visite la route /login
        When un user tenter de se compter avec un champ "<password>" qui contient moins de cinq caracteres
        Then la connexion echoue
        And l_operation provoque une erreur technique.

         Examples:
                | login  | password |                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
                | dan    | 125      |
                | brice  | 1345     |
                | meli    |1234      |

Scenario Outline: le user tente de se connecter avec un email qui ne correspond pas
        Given un user qui visite la route /login
        When un user tenter de se compter avec un champ "<email>" qui cne correspond pas
        Then la connexion echoue
        And l_operation provoque une erreur technique.

         Examples:
                | login  | email               |                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
                | dan    | dani@gmail.com      |
                | brice  | arleon@gmail.com    |