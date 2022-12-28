Feature: Modifier un compte specialiste
Dans ce site un admin doit pouvoir modifier le compte d_un specialiste et le specialiste aussi.

Background:
    Given le compte admin suivant existe
        |meli|

Scenario Outline: modifier correctement les informations d_un specialiste
    Given un admin "<intituleAdmin>" connecte et indentifie par le systeme
    And le compte specialiste a mettre a jour existe c_est a dire son "<login>" et son "<password>" existent deja dans le systeme
    When un admin tente de modifier un compte specialiste en renseignant correctement tous les champs
    Then le compte est mis a jour.
    
    Examples:

    |intituleAdmin|login|password|
    |meli         |dan  |12345   |
    |meli         |brice  |12345   |