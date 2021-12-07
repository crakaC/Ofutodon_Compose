package com.crakac.ofutodon.mastodon

import com.crakac.ofutodon.mastodon.api.*

interface Mastodon :
    AccountsResources,
    AnnouncemenstResources,
    AppsResources,
    InstanceResource,
    NotificationsResources,
    SearchResource,
    StatusesResources,
    TimelinesResources