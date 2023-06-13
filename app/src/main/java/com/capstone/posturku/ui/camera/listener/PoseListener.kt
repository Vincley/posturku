package com.capstone.posturku.ui.camera.listener

interface IPoseListener {
    fun onBadPosture()
    fun onGoodPosture()

    // Position Camera
    fun onBadCamera()
    fun onGoodCamera()
}

class PoseListener private constructor(){
    private val poseListeners: MutableList<IPoseListener> = mutableListOf()

    fun AddListener(l: IPoseListener){
        poseListeners.add(l)
    }

    fun RemoveListener(l: IPoseListener){
        poseListeners.remove(l)
    }

    fun onBadPosture(){
        poseListeners.forEach{l->
            l.onBadPosture()
        }
    }
    fun onGoodPosture(){
        poseListeners.forEach{l->
            l.onGoodPosture()
        }
    }

    // Position Camera
    fun onBadCamera(){
        poseListeners.forEach{l->
            l.onBadCamera()
        }
    }
    fun onGoodCamera(){
        poseListeners.forEach{l->
            l.onGoodCamera()
        }
    }
}