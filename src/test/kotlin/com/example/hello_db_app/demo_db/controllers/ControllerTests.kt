package com.example.hello_db_app.demo_db.controllers

import com.example.hello_db_app.demo_db.MainController
import com.example.hello_db_app.demo_db.Users
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest
class ControllerTests {

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var target: MainController

    @Before
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build()
    }

    @Test
    fun getAllUsersTest() {
        mockMvc.perform(
            get("/all")
        )
            .andExpect(status().isOk)
            .andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)
            )
    }

    @Test
    fun addNewUserTest() {
        mockMvc.perform(
            post("/add")
                .param("name", "unit_test")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Saved"))
    }

    @Test
    @Sql(statements = ["INSERT INTO users (name) VALUES ('update_data'); "])
    fun updateUserTest() {

        val lastUser: Users = target.userRepository.findAll().last()

        mockMvc.perform(
            post("/update")
                .param("id", lastUser.id.toString())
                .param("name", "updated!!!")
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Updated"))

    }

}
