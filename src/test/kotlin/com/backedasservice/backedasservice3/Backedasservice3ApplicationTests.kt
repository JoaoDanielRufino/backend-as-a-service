package com.backedasservice.backedasservice3

import org.json.JSONObject
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class Backedasservice3ApplicationTests {
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeAll
    fun setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun `Creating a successful model-structure called customer`() {
        val modelName = "customer"
        val structure: HashMap<String, Any> = hashMapOf(
            "name" to "String",
            "age" to "Int"
        )
        val payload = mapOf(
            "modelName" to modelName,
            "structure" to structure
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/model-structure")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject(payload).toString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `List all model-structures`() {
        mvc.perform(
            MockMvcRequestBuilders.get("/model-structure")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `Successfully add content to customer model`() {
        val payload = mapOf(
            "name" to "foo",
            "age" to 30
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/model-content/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject(payload).toString())
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

//    @Test
//    fun `List the content of customer model`() {
//        mvc.perform(
//            MockMvcRequestBuilders.get("/model-content/customer")
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//    }

//    @Test
//    fun `Fail to add content to customer model because of wrong type of age key`() {
//        val payload = mapOf(
//            "name" to "bar",
//            "age" to "30"
//        )
//
//        mvc.perform(
//            MockMvcRequestBuilders.post("/model-content/customer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JSONObject(payload).toString())
//        )
//            .andExpect(MockMvcResultMatchers.status().isBadRequest)
//    }

//    @Test
//    fun `Fail to create another model-structure called customer because of unique value for modelName`() {
//        val modelName = "customer"
//        val structure: HashMap<String, Any> = hashMapOf(
//            "name" to "String",
//            "age" to "Int"
//        )
//        val payload = mapOf(
//            "modelName" to modelName,
//            "structure" to structure
//        )
//
////        mvc.perform(
////            MockMvcRequestBuilders.post("/model-structure")
////                .contentType(MediaType.APPLICATION_JSON)
////                .content(JSONObject(payload).toString())
////        )
////            .andExpect(MockMvcResultMatchers.status().isCreated)
////            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
//
//        mvc.perform(
//            MockMvcRequestBuilders.post("/model-structure")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JSONObject(payload).toString())
//        )
//            .andExpect(MockMvcResultMatchers.status().isInternalServerError)
//    }
}
