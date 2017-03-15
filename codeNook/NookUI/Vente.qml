import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awVente
    visible: true
    width: 840
    height: 480

    signal sig_valider()

    Text {
        id: textHeader
        text: "Mettre un produit en vente"
        anchors.verticalCenterOffset: -190
        anchors.horizontalCenterOffset: -90
        font.bold: true
        font.pointSize: 18
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelNom
        x: 10
        text: "Nom du produit :"
        anchors.verticalCenterOffset: -140
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias nom: textFieldNom.text
    TextField {
        id: textFieldNom
        x: 140
        text: ""
        anchors.verticalCenterOffset: -140
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "Nom"
    }

    Text {
        id: textLabelDesc
        x: 10
        text: "Description :"
        anchors.verticalCenterOffset: -110
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias desc: textAreaDesc.text
    TextArea {
        id: textAreaDesc
        x: 140
        text: ""
        anchors.verticalCenterOffset: -40
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelPrix
        x: 10
        text: "Prix :"
        anchors.verticalCenterOffset: 60
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias prix: textFieldPrix.text
    TextField {
        id: textFieldPrix
        x: 140
        text: ""
        anchors.verticalCenterOffset: 60
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "Prix en euros"
    }

    property alias enchere: checkBoxEnchere.checked
    CheckBox {
        id: checkBoxEnchere
        x: 140
        text: "Ceci est une enchère"
        checked: false
        anchors.verticalCenterOffset: 90
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelEnchere
        x: 140
        text: "Fin de l'enchère :"
        anchors.verticalCenterOffset: 120
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias dateEnchere: textFieldEnchere.text
    TextField {
        id: textFieldEnchere
        x: 260
        text: ""
        anchors.verticalCenterOffset: 120
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "JJ/MM/AAAA"
    }

    Text {
        id: textLabelQte
        x: 10
        text: "Quantité :"
        anchors.verticalCenterOffset: 160
        anchors.verticalCenter: parent.verticalCenter
    }

    property alias quantite: textFieldQte.text
    TextField {
        id: textFieldQte
        x: 140
        text: ""
        anchors.verticalCenterOffset: 160
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "Qté disponible"
    }

    Text {
        id: textLabelImage
        x: 430
        text: "Insérer une photo"
        anchors.verticalCenterOffset: 10
        anchors.verticalCenter: parent.verticalCenter
    }

    Image {
        id: imageProduit
        x: 410
        anchors.verticalCenterOffset: -80
        anchors.verticalCenter: parent.verticalCenter
        source: "imageProduitVide.png"
    }

    Button {
        id: buttonValider
        x: 410
        text: "Mettre en vente"
        anchors.verticalCenterOffset: 180
        anchors.verticalCenter: parent.verticalCenter

        onClicked:
            awVente.sig_valider()
    }
}
