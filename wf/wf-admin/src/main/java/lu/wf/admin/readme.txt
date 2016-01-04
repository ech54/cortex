 en tout cas je te conseille cela:
- commence par définir un model interne à ce module
- perso= je partirais sur celui de maven (artifact Id / group Id / version)
- cela modélise ton jar
- ensuite tu rajoutes, deux trois info (on verra par la suite)
- tu pars sur un registre qui contient toutes les activités (les jars donc)
- après tu définis ton api : création d'une activité par api rest (donc tu alimentes ton registre /mongodb) suppression
- une seconde api : pour lier les activités ensemble
- exp: create --name monWorkfow --definition "activite1 > activte2 > activite3"
cela enverra un event vers mon module au final
- ensuite tu enrichis ton api: deploy --name monWorkfow
cela enverra un event pour instancier + déployer dans le container (que nous n'avons pas pour le moment)

 dans l'api rest, effectivement le path, le domain, le process name, et la version


 étape1: tu crées tout un tas de module (les step du wrk) packagé sous forme de jar
 étape2: tu utilises le module admin pour uploader tes modules en filant quelques paramètres (nom de la step, version etc)
 étape3: tu lies entre elles, ces steps. exp: etap1 > etap2 > etape3
 étape4: tu décides de déployer ton workflow. (dans le container donc)
 étape5: un utilisateur a maintenant accès qql part dans le portail du container à la nouvelle définition du workflow.
 étape6: il clique sur start de cette définition -> exp: une nouvelle souscription.
 étape7: côté engine, on instancie le workflow (une référence unique est créé), puis le container va charger tous les jar du workflow
 étape8: ensuite, on va présenter une vue commune, et une partie variera (c'est la vue de la step en fait)


