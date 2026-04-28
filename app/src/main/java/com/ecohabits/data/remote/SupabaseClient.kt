package com.ecohabits.data.remote
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest


object SupabaseClient {
    val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://ugacssjuvwcyukjtytrm.supabase.co",
        supabaseKey = "sb_publishable_z5oewjHrEgra8pE-rYUgmQ_6gw2JWs9"
    ){
        install(Postgrest)
    }
}