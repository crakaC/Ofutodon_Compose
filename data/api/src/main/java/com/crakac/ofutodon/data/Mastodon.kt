package com.crakac.ofutodon.data

import com.crakac.ofutodon.data.api.*

interface Mastodon :
    AccountsResources,
    AnnouncemenstResources,
    AppsResources,
    InstanceResource,
    NotificationsResources,
    SearchResource,
    StatusesResources,
    TimelinesResources
