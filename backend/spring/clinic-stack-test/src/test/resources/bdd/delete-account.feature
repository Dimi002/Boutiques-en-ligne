Feature: Supprimer un compte specialiste 

Background:
        Given l_admin suivant existe
        |meli|
    
Scenario Outline: supprimer le compte d_un specialiste de la clinique
    Given un admin "<intituleAdmin>" connecte et indentifie par le systeme
    When un admin trouve un compte specialiste  
    Then il supprime le compte.

    Examples:
        |intituleAdmin|
        |meli|