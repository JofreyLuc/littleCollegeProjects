import QtQuick 2.3
import QtQuick.Controls 1.2

Item {
    id: awResultat
    visible: true
    width: 640
    height: 480

    Text {
        id: textHeader
        text: "Recherche"
        anchors.verticalCenterOffset: -190
        anchors.horizontalCenterOffset: 0
        font.bold: true
        font.pointSize: 18
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textLabelCritere
        x: 10
        text: "Résultats de votre recherche pour :"
        anchors.verticalCenterOffset: -140
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textContentCritere
        x: 240
        text: "-"
        anchors.verticalCenterOffset: -140
        anchors.verticalCenter: parent.verticalCenter
    }
}
