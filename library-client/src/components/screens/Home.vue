<template>
    <b-container fluid>
        <b-row class="mt-4">
            <b-col>
                <h1>Administracion de libros</h1>
            </b-col>
        </b-row>
        <b-row class="mt-4">
            <b-row>
                <b-col>
                    <b-button id="creteBook" variant="primary" @click="showModal = true">Crear libro</b-button>
                    <b-modal v-model="showModal" title="Creacion de libro" @hide="showModal = false">
                        <b-form @submit="createBook" @reset="onReset">
                            <b-form-group id="input-group-1" label="Titulo:" label-for="input-1">
                                <b-form-input id="input-1" required></b-form-input>
                            </b-form-group>
                            <b-form-group id="input-group-2" label="Autor:" label-for="input-2">
                            <b-form-input id="input-2" required></b-form-input>
                            </b-form-group>
                            <b-form-group>
                                <label for="gender">Genero</label>
                                <b-form-select v-model="selected" :options="options"></b-form-select>
                            </b-form-group>
                            <label for="publicationYear">Año de publicacion</label>
                            <b-input-group class="mb-3">
                                <b-form-input id="example-input" v-model="value" type="text" placeholder="YYYY-MM-DD"
                                    autocomplete="off"></b-form-input>
                                <b-input-group-append>
                                    <b-form-datepicker v-model="value" button-only right locale="en-US"
                                        aria-controls="example-input" @context="onContext"></b-form-datepicker>
                                </b-input-group-append>
                            </b-input-group>
                            <b-button type="submit" variant="primary">Submit</b-button>
                            <b-button type="reset" variant="danger">Reset</b-button>
                        </b-form>
                    </b-modal>
                </b-col>
            </b-row>
            <b-row>
                <b-card v-for="item in items">
                    <b-card-text>
                        <b-row>
                            <b-col>
                                <h3>{{item.title}}</h3>
                            </b-col>
                            <b-col>
                                <b-button variant="primary">Editar</b-button>
                                <b-button variant="danger">Eliminar</b-button>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <p>{{item.author}}</p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <p>{{item.genre}}</p>
                            </b-col>
                        </b-row>
                        <b-row>
                            <b-col>
                                <p>{{item.publicationYear}}</p>
                            </b-col>
                        </b-row>
                    </b-card-text>
                </b-card>
            </b-row>
        </b-row>
    </b-container>
</template>


<script>
export default {
    data() {
        return {
            showModal: false
        }
    },
    methods: {
        createBook() {
            this.$router.push({ name: 'CreateBook' });
        }
    }
}

</script>