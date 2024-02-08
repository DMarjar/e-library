<template>
    <div>
        <div id="nav">
            <b-row>
                <div>
                    <b-nav pills>
                        <b-nav-item disabled>E-Libreria</b-nav-item>
                        <b-nav-item active>Creacion de libros</b-nav-item>
                    </b-nav>
                </div>
            </b-row>
        </div>
        <div id="content">
            <router-view></router-view>
        </div>
    </div>
</template>