import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        routes: []
    },
    mutations: {
        initMenu(state, menus){
            state.routes = menus;
        }
    }
});
