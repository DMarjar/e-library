import vueRouter from 'vue-router';
import Vue from 'vue';



Vue.use(vueRouter);

const routes = [
{
    path: '/',
    redirect: '/home'
},
{
    path:'*',
    component: () => import('../components/screens/ErrorPage/Error404.vue')
},

{
    path:'/',
    component:{
        render (c){
            return c('router-view')
        }
    },
    children: [
        {
            path:'/layout',
            name:'layout',
            component: () => import('../components/screens/Layout.vue'),

            children: [
                {
                    path:'/home',
                    name:'home',
                    component: () => import('../components/screens/Home.vue')
                },
            ]
        }, 
        
    ]

}
]

const router = new vueRouter({routes})
export default router;



