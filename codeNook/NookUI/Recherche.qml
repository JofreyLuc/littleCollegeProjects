import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awRecherche
    visible: true
    width: 740
    height: 480

    Text {
        id: textHeader
        text: "Que voulez-vous chercher ?"
        anchors.verticalCenterOffset: -50
        anchors.horizontalCenterOffset: -100
        font.bold: true
        font.pointSize: 18
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelRecherche
        x: 0
        text: "Demandez, nook l'avons"
        anchors.verticalCenterOffset: 0
        anchors.horizontalCenterOffset: -100
        anchors.verticalCenter: parent.verticalCenter
        anchors.horizontalCenter: parent.horizontalCenter
    }

    TextField {
        id: textFieldRecherche
        width: 350
        height: 22
        text: ""
        anchors.verticalCenterOffset: 30
        anchors.horizontalCenterOffset: -100
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        placeholderText: "Critères de recherche"
    }

    Button {
        id: buttonValider
        text: "Envoyer"
        anchors.verticalCenterOffset: 70
        anchors.horizontalCenterOffset: -100
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }
}
