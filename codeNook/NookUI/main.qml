import QtQuick 2.3
import QtQuick.Controls 1.2

ApplicationWindow {
    id: awPrincipale
    visible: true
    width: 740
    height: 480
    title: qsTr("Nook - Fenêtre principale")

    menuBar: AltMenuBar {}

    Text {
        id: textHeader
        text: "Bienvenue !"
        font.bold: true
        font.pointSize: 18
        anchors.verticalCenterOffset: -60
        anchors.horizontalCenterOffset: -100
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    Text {
        id: textMessage1
        text: "Vous allez nook adorer"
        anchors.verticalCenterOffset: -30
        anchors.horizontalCenterOffset: -100
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
    }

    SideBar {
        id: sideBar1
        x: 471
        anchors.right: parent.right
        anchors.rightMargin: 0
        anchors.top: parent.top
        anchors.topMargin: 0
    }
}
