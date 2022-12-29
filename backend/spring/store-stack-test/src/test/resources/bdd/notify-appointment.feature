Feature: Notifier un specialiste pour une nouvelle prise de rendez vous
Le systeme doit pouvoir notifier le specialiste concerne a chaque qu_il y a une nouvelle prise de rendez vous

Background:
les specialistes suivant existent

    Given 
        |meli |
        |dan  |
        |brice|

    Given 
        |ophtamologue|
        |pediatre    |
        |neurologue  |

    Scenario Outline: 
        Given un internaute ayant cree un rendez vous avec  "<nomSpecialiste>" qui est "<nomSpecialite>"
        Then le specialiste "<nomSpecialiste>" est notifie

        Examples: 
        