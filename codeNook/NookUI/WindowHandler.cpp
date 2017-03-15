#include "WindowHandler.hpp"

WindowHandler::WindowHandler() {

    this->vRecherche.setSource(QUrl("qrc:/Recherche.qml"));
    this->vRechercheU.setSource(QUrl("qrc:/RechercheU.qml"));
    this->vProfil.setSource(QUrl("qrc:/Profil.qml"));
    this->vVente.setSource(QUrl("qrc:/Vente.qml"));
    this->vConnexion.setSource(QUrl("qrc:/Connexion.qml"));
    //this->vInscription.setSource(QUrl("qrc:/Inscription.qml"));
    //this->vDeconnexion.setSource(QUrl("qrc:/Deconnexion.qml"));

    QObject::connect(vVente.rootObject(), SIGNAL(sig_valider()), this, SLOT(validerVente()));
}

void WindowHandler::setUtilisateur(Utilisateur *u) {

    this->u = u;
}

void WindowHandler::setBdd(Bdd *b) {

    this->bdd = b;
}

void WindowHandler::setProfil() {

    QObject *profil = this->vProfil.rootObject();

    profil->setProperty("pseudo", QVariant(QString::fromStdString(this->u->getPseudo())));
    profil->setProperty("mail", QVariant(QString::fromStdString(this->u->getMail())));
    profil->setProperty("adresse", QVariant(QString::fromStdString(this->u->getAdresseNum())));
    profil->setProperty("ville", QVariant(QString::fromStdString(this->u->getAdresseVille()+" - "+this->u->getAdresseCP())));
    profil->setProperty("telephone", QVariant(QString::fromStdString(this->u->getNumero())));
}

void WindowHandler::triggerRecherche() {

    this->vRecherche.show();
}

void WindowHandler::triggerRechercheU() {

    this->vRechercheU.show();
}

void WindowHandler::triggerProfil() {

    this->vProfil.show();
}

void WindowHandler::triggerVente() {

    this->vVente.show();
}

void WindowHandler::triggerConnexion() {

    this->vConnexion.show();
}

void WindowHandler::triggerInscription() {

    this->vInscription.show();
}

void WindowHandler::triggerDeconnexion() {

    this->vDeconnexion.show();
}

void WindowHandler::quit() {

    exit(0);
}

void WindowHandler::validerVente() {

    QObject *vente = this->vVente.rootObject();

    int id = 1;
    std::string nom = vente->property("nom").toString().toUtf8().data();
    int prix = vente->property("prix").toInt();
    int qte = vente->property("quantite").toInt();
    bool enchere = vente->property("enchere").toBool();

    Produit p(id, nom, prix, qte, enchere);
    this->u->vendreProduit(id, nom, prix, qte, enchere);

    //Debug
    //std::cout << this->bdd->getProduit(id) << std::endl;
    std::cout << "Ajout du produit :" << std::endl;
    std::cout << p << std::endl;
}
