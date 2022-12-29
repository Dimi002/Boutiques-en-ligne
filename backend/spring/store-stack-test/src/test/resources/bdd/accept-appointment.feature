Feature: Accepter un rendez vous avec un patient
cette fonctionnalite permet a un patient de prendre un rendez avec le medecin de son choix dans une specialite precise. 

 Background:
      Given les specialistes suivants existent 
         |Momo  |
         |Ndadji|
         |Tuem  |
      Given les specialites suivantes existent 
         |ophtamologue|
         |neurologue  |
         |pediatre    |
         |cardiologue | 

 Scenario Outline: le patient prend rendez vous avec les donnees correctement renseignees
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    Then une notification est envoyee au specialiste concerne
    And le specialiste est libre : le rendez vous est accepter.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                              | date         | heure    |
   |Meli          | ophtamologue  |Daniela        | +237691971542    | daniela@gmail.com   | c_est Daniela qui veut se faire consulter les yeux  | °8/09/2022   |12:30:00  |
   |Meli          | ophtamologue  |Daniela        | +237691971542    | daniela@gmail.com   | c_est encore moi                                  | 14/09/2022   | 12:30:00  |


Scenario Outline: le patient prend rendez vous avec son nom non fourni
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner son nom dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut le nom du patient.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                       | date         | heure    |
   |Meli          | ophtamologue  |        | +237691971542    | daniela@gmail.com   | c_est Daniela qui veut se faire consulter les yeux  | 08/09/2022   |12:30:00  |
   |Meli          | ophtamologue  |      | +237691971542    | daniela@gmail.com   | c_est encore moi                                  | 14/09/2022   | 12:30:00  |

Scenario Outline: le patient prend rendez vous avec le specialiste sans fourni son numero
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner son numero de telephone dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut le numero du patient.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                 | date         | heure    |
   |Meli          | ophtamologue  |Daniela        |     | daniela@gmail.com   | c_est Daniela qui veut se faire consulter les yeux  | 08/09/2022   |12:30:00  |
  
Scenario Outline: le patient prend rendez vous avec le specialiste sans fourni la date du rendez vous
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner la date du rdv dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut une date.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                 | date         | heure     |
   |Meli          | ophtamologue  |Daniela        |   +237691971542  | daniela@gmail.com   | c_est Daniela qui veut se faire consulter les yeux  |   |12:30:00  |

Scenario Outline: le patient prend rendez vous avec le specialiste sans fourni un message desriptif
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner un message descriptif dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut le message.

    Examples:

|nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message    | date         | heure|
   |Meli          | ophtamologue  |Daniela        |   +237691971542  | daniela@gmail.com   |   | 08/09/2022   |12:30:00  |

Scenario Outline: le patient prend rendez vous avec le specialiste sans fourni une heure
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner une heure dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut une heure pour le rdv.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                              | date         | heure              |
   |Meli          | ophtamologue  |Daniela        | +237691971542    | daniela@gmail.com   | c_est Daniela qui veut se faire consulter les yeux  | 08/09/2022   |1 |



Scenario Outline: le patient prend rendez vous avec le mail mal formate
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And le mail est mal formate
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut une addresse au bon format.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                              | date         | heure     |
   |Meli          | ophtamologue  |Daniela        | +237691971542    | daniela@.com   | c_est Daniela qui veut se faire consulter les yeux  | 08/09/2022   |12:30:00  |
   |Meli          | ophtamologue  |Daniela        | +237691971542    | daniela@gmail  | c_est encore moi                                  | 14/09/2022   | 12:30:00  |

Scenario Outline: le patient prend rendez vous avec le specialiste sans fourni son email
    Given un internaute qui souhaite prendre un rendez vous avec un specialiste "<nomSpecialiste>" dont la specialite est "<nomSpecialite>"
    When il renseigne normalement les informations necessaires pour le rendez vous telles que son "<nomPatient>", "<numeroPatient>", "<emailPatient>", "<message>", "<date>", "<heure>"
    And il oublie de renseigner son mail dans le formulaire
    Then le systeme provoque une erreur et affiche un message precisant qu_il faut le mail du patient.

    Examples:

   |nomSpecialiste| nomSpecialite |nomPatient     | numeroPatient    | emailPatient        |message                                              | date         | heure    |
   |Meli          | ophtamologue  |Daniela        |  +237691971542   |   | c_est Daniela qui veut se faire consulter les yeux  | 08/09/2022   |12:30:00  |



