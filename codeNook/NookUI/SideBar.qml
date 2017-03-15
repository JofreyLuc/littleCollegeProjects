import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: sideBar
    objectName: "sideBar"
    width: 200
    height: 480

    signal sig_recherche()
    signal sig_rechercheu()
    signal sig_profil()
    signal sig_vente()
    signal sig_connexion()
    signal sig_inscription()
    signal sig_deconnexion()

    Text {
        id: textLabelStatus
        y: 20
        text: "Déconnecté"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter
    }

    Button {
        id: buttonRecherche
        y: 50
        text: "Rechercher un produit"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_recherche()
        }
    }

    Button {
        id: buttonRechercheU
        y: 80
        text: "Rechercher un profil"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_rechercheu()
        }
    }

    Button {
        id: buttonProfil
        y: 130
        text: "Consulter mon profil"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_profil()
        }
    }

    Button {
        id: buttonVente
        y: 180
        text: "Vendre un produit"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_vente()
        }
    }

    Button {
        id: buttonConnexion
        y: 230
        text: "Connexion"
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_connexion()
        }
    }

    Button {
        id: buttonInscription
        y: 260
        text: "Inscription"
        enabled: false
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_inscription()
        }
    }

    Button {
        id: buttonDeconnexion
        y: 290
        text: "Déconnexion"
        enabled: false
        anchors.horizontalCenterOffset: 0
        anchors.horizontalCenter: parent.horizontalCenter

        onClicked: {
            sideBar.sig_deconnexion()
        }
    }
}
