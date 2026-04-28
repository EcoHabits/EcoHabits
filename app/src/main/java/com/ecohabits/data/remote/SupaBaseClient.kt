package com.ecohabits.data.remote

val supabase = createSupabaseClient(
    supabaseUrl = "https://ugacssjuvwcyukjtytrm.supabase.co",
    supabaseKey = "sb_publishable_z5oewjHrEgra8pE-rYUgmQ_6gw2JWs9"
) {
    install(Postgrest)
}